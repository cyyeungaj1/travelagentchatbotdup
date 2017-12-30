
heroku pg:psql postgresql-asymmetrical-26863 --app travelagentchatbot
CREATE TABLE "line_user" (id SERIAL UNIQUE, line_id text NOT NULL);




heroku
kaminglo2001@gmail.com
pw:
herokulo4a20
heroku pg:psql postgresql-round-66703 --app comp3111travelagent
lg4 05
lg4 17

get all booking
select * from tourlist where tour_id in (select tour_id from booking where customer_id = 'A777666');


select customer_id from customer where customer_line = 'U2336052073d2a3192d088b3215554ee1';

select distinct tour_id from booking where tour_id in (select tour_id from tourlist where tour_fee >= 599 and tour_fee <= 699);
/*1. get MIN tour fee record from client*/
select min(tour_fee) from tourlist where tour_id in (select tour_id from booking where customer_id = 'A777666');
/*2. get MAX tour fee record from client*/
select max(tour_fee) from tourlist where tour_id in (select tour_id from booking where customer_id = 'A777666');
/*3. get MOST COMMON country_id record from client*/
select country_id, count(country_id) as country from tourlist  where tour_id in (select tour_id from booking where customer_id = 'A777666') group by country_id order by country desc limit 1;

/*if client doesn't have booking record*/
select b.tour_id, count(b.tour_id) as tour from booking as b join tourlist as t on b.tour_id = t.tour_id where  t.departure_date > '2017-10-15' and t.tour_cap > (select sum(no_of_adult) + sum(no_of_children) as no_of_ppl from booking where tour_id = b.tour_id) group by b.tour_id order by tour desc limit 1;

/*if client has booking record, return tour_id base on (BETWEEN 1 AND 2) OR (3)*/
select b.tour_id, count(b.tour_id) as tour, sum(b.no_of_adult) + sum(b.no_of_children) as no_of_ppl from booking as b join tourlist as t on b.tour_id = t.tour_id  where ((t.tour_fee >= 599 and t.tour_fee <= 699) or t.country_id = 1) and t.departure_date > '2017-10-15' and t.tour_cap > (select sum(no_of_adult) + sum(no_of_children) as no_of_ppl from booking where tour_id = b.tour_id) group by b.tour_id order by tour desc limit 1;



select tour_id, count(tour_id) as tour from booking group by tour_id order by tour desc limit 1;
select tour_id, count(tour_id) as tour from booking where tour_id in (select tour_id from tourlist where ((tour_fee >= 599 and tour_fee <= 699) or country_id = 1) and departure_date > '2017-11-17') group by tour_id order by tour desc limit 1;


select b.customer_id, count(b.customer_id) as count from booking as b join customer as c on b.customer_id = c.customer_id group by b.customer_id  order by count;
select customer_id, name, customer_line from customer where customer_id = 'A333444' or customer_id = 'A555444' or customer_id = 'A555333' or customer_id = 'A444888';
 update customer set customer_line = 'Uac826d7da3c84e5472ef767d4a8f9405' where customer_id = 'A333444';
    update customer set customer_line = 'U027e5e80d85c34dbccc2e286bfe25d6f' where customer_id = 'A555444';
       update customer set customer_line = 'Uf97203d99ffb96b3d4e6e1f9e5134562' where customer_id = 'A555333';


         update customer set name = 'Jerome' where customer_id = 'A333444';
            update customer set name = 'TatMing' where customer_id = 'A555444';
               update customer set name = 'Siu' where customer_id = 'A555333';

CREATE TABLE "booking" (
    "date" "date",
    "booking_id" character varying(255),
    "customer_id" character varying(255),
    "tour_id" character varying(255),
    "no_of_adult" integer,
    "no_of_children" integer,
    "no_of_toodler" integer,
    "tour_fee" double precision,
    "amount_paid" double precision,
    "special_request" "text",
    "pay_confirmed" boolean,
    "additional_charge" double precision
);


CREATE TABLE "country" (
    "country_id" integer,
    "country" character varying(255)
);


CREATE TABLE "customer" (
    "name" character varying(255),
    "customer_id" character varying(255) NOT NULL,
    "phone_no" character varying(255),
    "age" integer,
    "customer_line" "text"
);

CREATE TABLE "faq" (
    "question_no" integer,
    "question" "text",
    "answer" "text"
);


 CREATE TABLE "promotion" (
    "tour_id" character varying(255) NOT NULL,
    "promotion_start" "date",
    "promotion_end" "date",
    "discount" numeric(5,2),
    "discount_description" "text"
);

CREATE TABLE "region" (
    "country_id" integer,
    "region_id" integer,
    "region" character varying(255)
);

CREATE TABLE "tourlist" (
    "country_id" integer,
    "region_id" integer,
    "tour_id" character varying(255) NOT NULL,
    "tour_name" "text",
    "tour_shortdec" "text",
    "hotel" character varying(255),
    "duration" integer,
    "departure_date" "date",
    "tour_cap" integer,
    "min_req_cap" integer,
    "tour_fee" double precision,
    "tour_guide" character varying(255),
    "tour_guide_line_ac" character varying(255),
    "tour_confirmed" boolean DEFAULT false
);


insert into booking values ('2017-01-11', 'bk_id 1', 'cus_id 1', 'tour_id 1', 2, 3, 4, 499, 399, 'no', false, 0);
insert into booking values ('2017-01-15', 'bk_id 2', 'cus_id 1', 'tour_id 1', 2, 3, 4, 499, 399, 'no', false, 0);

insert into country values (1, 'China');
insert into country values (2, 'Japan');
insert into country values (3, 'Taiwan');

insert into customer values ('matthew1', 'cus_id 1', '12345678', 22, '');
insert into customer values ('matthew2', 'cus_id 2', '12345678', 22, '');
insert into customer values ('matthew3', 'cus_id 3', '12345678', 22, '');
insert into customer values ('matthew4', 'cus_id 4', '12345678', 22, '');
insert into customer values ('matthew5', 'cus_id 5', '12345678', 22, '');
insert into customer values ('matthew6', 'cus_id 6', '12345678', 22, '');
insert into customer values ('matthew7', 'cus_id 7', '12345678', 22, '');

insert into faq values (1, 'question 1', 'ans 1');
insert into faq values (2, 'question 2', 'ans 2');
insert into faq values (3, 'question 3', 'ans 3');
insert into faq values (4, 'question 4', 'ans 4');
insert into faq values (5, 'question 5', 'ans 5');
insert into faq values (6, 'question 6', 'ans 6');

insert into promotion values ('1', '2017-10-22', '2017-10-28', 0.15, '');

insert into region values (1, 1, 'region1');
insert into region values (1, 2, 'region2');
insert into region values (2, 3, 'region3');
insert into region values (1, 4, 'region4');
insert into region values (3, 5, 'region5');
insert into region values (2, 6, 'region6');

insert into tourlist values (1, 1, 'tourid1', 'tour name1', 'tour description 1', 'hotel 1', 2, '2017-10-11', 20, 4, 399, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (1, 2, 'tourid2', 'tour name2', 'tour description 2', 'hotel 2', 2, '2017-10-14', 20, 4, 299, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (2, 3, 'tourid3', 'tour name3', 'tour description 3', 'hotel 3', 2, '2017-10-20', 20, 4, 499, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (1, 4, 'tourid4', 'tour name4', 'tour description 4', 'hotel 4', 2, '2017-10-30', 20, 4, 599, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (3, 5, 'tourid5', 'tour name5', 'tour description 5', 'hotel 5', 2, '2017-10-25', 20, 4, 699, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (1, 1, 'tourid6', 'tour name6', 'tour description 6', 'hotel 6', 2, '2017-11-03', 20, 4, 899, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (1, 1, 'tourid7', 'tour name7', 'tour description 7', 'hotel 7', 2, '2017-10-04', 20, 4, 199, 'tour guide 1', 'tour line 1', false);
insert into tourlist values (1, 1, 'tourid8', 'tour name8', 'tour description 8', 'hotel 8', 2, '2017-12-01', 20, 4, 299, 'tour guide 1', 'tour line 1', false);
