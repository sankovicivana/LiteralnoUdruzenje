INSERT INTO merchant (id_long,error_url,failed_url,id,password,success_url,bank_id)
VALUES (1,'greska','fail','1','123','uspeh',1);

INSERT INTO merchant_payment_types(payment_types,merchant_id_long)
VALUES (0,1);
INSERT INTO merchant_payment_types(payment_types,merchant_id_long)
VALUES (1,1);
INSERT INTO merchant_payment_types(payment_types,merchant_id_long)
VALUES (2,1);
