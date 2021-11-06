ALTER TABLE maintenance_request ADD COLUMN parts jsonb;
ALTER TABLE upgrade_request     ADD COLUMN parts jsonb;