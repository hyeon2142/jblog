desc user;
desc 
insert 
into user 
values('bolt', '창현', 1234, now());

select count(*)
from post
where category_no = 10;

select c.no, c.name, c.desc, c.blog_id as blogId, (select count(*) from post where category_no = c.no) as postCount
from category c;

select * from user;
select * from blog;
select * from category;
select * from post;
delete from user where id = ' Feynman';
delete from blog where id = ' Feynman';
delete from category where no = 4;
delete from post where no = 12;


desc category;
select c.no, c.name, c.desc, c.blog_id as blogId 
from category c
order by c.no desc;

insert into category values(null, 'test', 'java', 'nami');
delete from category where no = 4;

select distinct name from category where blog_id = 'eogkdfh@gmail.com';

select * from category;

desc post;

insert into post values(null, 'test', 'test', now(), 10);

select * from post
order by reg_date desc;

select no, title, contents, reg_date as regDate, category_no as categoryNo 
from post
order by reg_date desc
limit 0, 1;

select no, title, contents, date_format(reg_date, '%Y/%m/%d %h:%i:%s') as regDate, category_no as categoryNo 
from post
order by reg_date desc
limit 0, 1;

delete from post where no = 1;

select p.no, p.title, p.contents, date_format(p.reg_date, '%Y/%m/%d %h:%m:%s') as regDate, p.category_no as catgoryNo
from post p join category c on p.category_no = c.no
where c.blog_id = 'eogkdfh@gmail.com'
order by regDate desc;

select p.no, p.title, p.contents, date_format(p.reg_date, '%Y/%m/%d') as regDate, p.category_no as catgoryNo
from post p join category c on p.category_no = c.no
where c.blog_id = 'eogkdfh@gmail.com'
and p.category_no = 5
order by regDate desc;