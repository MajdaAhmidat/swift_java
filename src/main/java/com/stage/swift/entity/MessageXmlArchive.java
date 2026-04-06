package com.stage.swift.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "swift", name = "message_xml_archive")
public class MessageXmlArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_vrt_emis")
    private Long idVrtEmis;

    @Column(name = "id_vrt_recu")
    private Long idVrtRecu;

    @Column(name = "flow_type", nullable = false, length = 20)
    private String flowType;

    @Column(name = "message_type", length = 50)
    private String messageType;

    @Column(name = "payload_hash", nullable = false, length = 64)
    private String payloadHash;

    @Column(name = "original_size_bytes", nullable = false)
    private Long originalSizeBytes;

    @Column(name = "is_compressed", nullable = false)
    private Boolean compressed = Boolean.TRUE;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "payload_gzip", nullable = false, columnDefinition = "bytea")
    private byte[] payloadGzip;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdVrtEmis() { return idVrtEmis; }
    public void setIdVrtEmis(Long idVrtEmis) { this.idVrtEmis = idVrtEmis; }
    public Long getIdVrtRecu() { return idVrtRecu; }
    public void setIdVrtRecu(Long idVrtRecu) { this.idVrtRecu = idVrtRecu; }
    public String getFlowType() { return flowType; }
    public void setFlowType(String flowType) { this.flowType = flowType; }
    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
    public String getPayloadHash() { return payloadHash; }
    public void setPayloadHash(String payloadHash) { this.payloadHash = payloadHash; }
    public Long getOriginalSizeBytes() { return originalSizeBytes; }
    public void setOriginalSizeBytes(Long originalSizeBytes) { this.originalSizeBytes = originalSizeBytes; }
    public Boolean getCompressed() { return compressed; }
    public void setCompressed(Boolean compressed) { this.compressed = compressed; }
    public byte[] getPayloadGzip() { return payloadGzip; }
    public void setPayloadGzip(byte[] payloadGzip) { this.payloadGzip = payloadGzip; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
