-- MIGRATION TO HAPPEN

ALTER TABLE metric MODIFY COLUMN id VARCHAR(36);

DELETE FROM metric WHERE 1;

-- Insert data
INSERT IGNORE INTO `metric` (`id`, `metric`, `display`) VALUES
	('3a072577-5174-41c7-8d8f-bfc9645d9ef8', 'Per peice', '/pc'),
	('40b5eb08-afc1-4aee-815d-305d8c60386a', 'Per gram', '/g'),
	('638bf9e3-cc38-4a4d-a623-62a2a78e476a', 'Per hour', '/hr'),
	('69e45ebb-ffae-46a1-a1e5-4fc838138374', 'None', ' '),
	('781c7ae0-e526-4cb6-bf0a-4464d3996c2a', 'Per kilogram', '/kg'),
	('9513e665-f0d2-40ea-9229-c0abb4ae3f08', 'Per litre', '/litre'),
	('a5269ae0-835d-48ce-93dd-4b90dfba177d', 'Per Sq. Meter', '/sq. m'),
	('b345171d-5652-46a2-82cb-6f725c48ae7d', 'Per meter', '/m');