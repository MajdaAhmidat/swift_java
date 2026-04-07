-- Suppression complète de la table d'archive XML.
-- A exécuter manuellement en base de données.

DROP TABLE IF EXISTS swift.message_xml_archive CASCADE;
