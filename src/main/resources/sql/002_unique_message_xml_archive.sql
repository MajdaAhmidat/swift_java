-- Empêche les doublons de XML pour un même virement EMIS
CREATE UNIQUE INDEX IF NOT EXISTS uq_message_xml_archive_emis_hash
ON swift.message_xml_archive (id_vrt_emis, payload_hash)
WHERE id_vrt_emis IS NOT NULL;

-- Empêche les doublons de XML pour un même virement RECU
CREATE UNIQUE INDEX IF NOT EXISTS uq_message_xml_archive_recu_hash
ON swift.message_xml_archive (id_vrt_recu, payload_hash)
WHERE id_vrt_recu IS NOT NULL;
