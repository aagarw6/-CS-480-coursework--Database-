use cs480fall12;

Select cname,ccity from customer join buyvehicle on ssn=BVSSN where year=2005;

Select cname from customer join (select * from buyvehicle join vehicle on BVVIN = vin where vmaker = "honda") as temp on BVSSN=ssn where price>20000 and year between 2005 and 2010;

Select customer.cname from customer left join (Select ssn,BVVIN from customer join buyvehicle on BVSSN=SSN) as temp on temp.ssn = customer.ssn  where temp.BVVIN is NULL;

SELECT DISTINCT buyvehicle.BVSSN, buyvehicle_1.BVSSN FROM vehicle INNER JOIN buyvehicle  ON  vehicle.vin = buyvehicle.BVVin, vehicle AS vehicle_1 INNER JOIN buyvehicle AS buyvehicle_1  ON vehicle_1.vin = buyvehicle_1.BVVin WHERE buyvehicle.BVSSN<>buyvehicle_1.BVSSN and vehicle.vin<>vehicle_1.vin and vehicle.vmaker=vehicle_1.vmaker and vehicle.vmodel=vehicle_1.vmodel and vehicle.vyear=vehicle_1.vyear and buyvehicle.BVSSN<=buyvehicle_1.BVSSN;

Select a_ssn as SSN from (select a.BVSSN as a_ssn,b.vmaker as b_vmaker from (select BVSSN,vmaker from vehicle join buyvehicle on BVVIN=vin where vmaker = 'nissan') as a left join (select BVSSN,vmaker from vehicle join buyvehicle on BVVIN=VIN where vmaker not like 'nissan') as b on a.BVSSN=b.BVSSN) as c, customer where a_ssn=customer.SSN and cgender = "female" and b_vmaker is  null;

Select Distinct vmaker,vmodel from vehicle join buyvehicle on BVVIN=vin where price>=10000 ;

Select vmaker,vmodel from (select vmaker,vmodel,(COUNT(vmodel)) as count from vehicle full join buyvehicle on VIN= BVVIN group by (vmodel))as temp where count = (select MAX(count) from (select vmaker,vmodel,(COUNT(vmodel)) as count from vehicle full join buyvehicle on VIN= BVVIN group by (vmodel))as temp2);

Select cname from customer where SSN in (select BVSSN from (select * from buyvehicle join vehicle  on BVVIN=VIN) as temp2,(select AVG(price) as average_price,vmaker,vmodel,vyear from buyvehicle join vehicle on BVVIN=VIN group by vmaker,vmodel,vyear) as temp where price>average_price and temp2.vmaker=temp.vmaker and temp2.vmodel=temp.vmodel and temp2.vyear=temp.vyear);

Select vmodel,vmaker,MAX(price) as max_price from vehicle inner join buyvehicle on vehicle.vin=buyvehicle.BVVIN group by vmaker;

Select vmaker,sum(price) as total_price from (select * from buyvehicle inner join vehicle on buyvehicle.BVVIN=vehicle.vin where BVSSN in (select BVSSN from buyvehicle group by BVSSN having count(*) >=3)) as temp group by vmaker;


