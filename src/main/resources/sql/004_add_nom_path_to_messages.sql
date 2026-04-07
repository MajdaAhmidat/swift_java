-- Ajout des metadonnees fichier XML (nom + path) pour lecture depuis SAVE.
ALTER TABLE IF EXISTS swift.message_emis
    ADD COLUMN IF NOT EXISTS nom VARCHAR(255),
    ADD COLUMN IF NOT EXISTS path VARCHAR(1024);

ALTER TABLE IF EXISTS swift.message_recu
    ADD COLUMN IF NOT EXISTS nom VARCHAR(255),
    ADD COLUMN IF NOT EXISTS path VARCHAR(1024);
