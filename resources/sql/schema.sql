-- :name create-urls-table
-- :command :execute
-- :result :raw
-- :doc Create urls table
CREATE TABLE "urls" (
    "id" serial,
    "url" text,
    "created_at" TIMESTAMP DEFAULT now(),
    PRIMARY KEY ("id")
);

-- :name drop-urls-table :!
DROP TABLE IF EXISTS "urls" CASCADE;

-- :name insert-url :<!
-- :doc Insert a single url
INSERT INTO "urls" ("url")
VALUES (:url) RETURNING "id"

-- :name all-urls :? :*
SELECT "id", "url", "created_at" FROM "urls"
ORDER BY "created_at"

-- :name url-by-id :? :1
-- :doc Get url by id
SELECT "url", "created_at"  FROM "urls"
WHERE "id" = :id
