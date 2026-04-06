package com.stage.swift.service.mx.impl;

import com.stage.swift.entity.MessageXmlArchive;
import com.stage.swift.repository.MessageXmlArchiveRepository;
import com.stage.swift.service.mx.MessageXmlArchiveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class MessageXmlArchiveServiceImpl implements MessageXmlArchiveService {

    private final MessageXmlArchiveRepository repository;

    public MessageXmlArchiveServiceImpl(MessageXmlArchiveRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void archiveForVirementEmis(Long idVrtEmis, String flowType, String messageType, String rawXml) {
        if (idVrtEmis == null || rawXml == null || rawXml.trim().isEmpty()) {
            return;
        }
        MessageXmlArchive row = baseArchive(flowType, messageType, rawXml);
        if (repository.existsByIdVrtEmisAndPayloadHash(idVrtEmis, row.getPayloadHash())) {
            return;
        }
        row.setIdVrtEmis(idVrtEmis);
        repository.save(row);
    }

    @Override
    @Transactional
    public void archiveForVirementRecu(Long idVrtRecu, String flowType, String messageType, String rawXml) {
        if (idVrtRecu == null || rawXml == null || rawXml.trim().isEmpty()) {
            return;
        }
        MessageXmlArchive row = baseArchive(flowType, messageType, rawXml);
        if (repository.existsByIdVrtRecuAndPayloadHash(idVrtRecu, row.getPayloadHash())) {
            return;
        }
        row.setIdVrtRecu(idVrtRecu);
        repository.save(row);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> findLatestXmlByVirementEmisId(Long idVrtEmis) {
        return repository.findTopByIdVrtEmisOrderByCreatedAtDesc(idVrtEmis)
                .map(this::extractXml);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> findLatestXmlByVirementRecuId(Long idVrtRecu) {
        return repository.findTopByIdVrtRecuOrderByCreatedAtDesc(idVrtRecu)
                .map(this::extractXml);
    }

    private MessageXmlArchive baseArchive(String flowType, String messageType, String rawXml) {
        byte[] rawBytes = rawXml.getBytes(StandardCharsets.UTF_8);
        MessageXmlArchive row = new MessageXmlArchive();
        row.setFlowType(flowType != null ? flowType : "UNKNOWN");
        row.setMessageType(messageType);
        row.setOriginalSizeBytes((long) rawBytes.length);
        row.setCompressed(Boolean.TRUE);
        row.setPayloadHash(sha256(rawBytes));
        row.setPayloadGzip(gzip(rawBytes));
        return row;
    }

    private String extractXml(MessageXmlArchive row) {
        byte[] payload = row.getPayloadGzip();
        if (payload == null) {
            return "";
        }
        byte[] bytes = Boolean.TRUE.equals(row.getCompressed()) ? gunzip(payload) : payload;
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private byte[] gzip(byte[] data) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data);
            gzip.finish();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de compresser le XML", e);
        }
    }

    private byte[] gunzip(byte[] compressed) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
             GZIPInputStream gis = new GZIPInputStream(bis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de decompresser le XML", e);
        }
    }

    private String sha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            StringBuilder sb = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponible", e);
        }
    }
}
