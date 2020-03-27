select *
from site
where site_id not in (select site_id
from reservation
where from_date < '2020-02-16' and to_date >= '2020-02-16'
or    from_date < '2020-02-18' and to_date >= '2020-02-18'
or    from_date > '2020-02-16' and to_date <= '2020-02-18')
AND campground_id = 1;