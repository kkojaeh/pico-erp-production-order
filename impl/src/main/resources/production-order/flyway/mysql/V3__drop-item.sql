drop table pcr_production_order_item;
ALTER TABLE pcr_production_order DROP name;

ALTER TABLE pcr_production_order ADD item_id binary(16);
ALTER TABLE pcr_production_order ADD item_spec_id binary(16);
ALTER TABLE pcr_production_order ADD item_spec_code varchar(20);
ALTER TABLE pcr_production_order ADD quantity decimal(19,2);
