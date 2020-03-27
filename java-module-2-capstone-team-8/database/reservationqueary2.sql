select *
from site
where site_id not in (select site_id
from reservation
where from_date < '2020-02-16' and to_date >= '2020-02-16'
or    from_date < '2020-02-18' and to_date >= '2020-02-18'
or    from_date > '2020-02-16' and to_date <= '2020-02-18')
AND campground_id = 1;

select site.site_id, site.campground_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, campground.daily_fee,
((reservation.to_date ::date - reservation.from_date ::date) * daily_fee) as cost_of_stay
from site
inner join campground
on site.campground_id = campground.campground_id
inner join reservation 
on site.site_id = reservation.site_id
where site.site_id not in (select site_id
from reservation
where from_date <= '2021-02-16' and to_date > '2021-02-18'
or    from_date < '2021-02-16' and to_date >= '2021-02-18'
or    from_date > '2021-02-16' and to_date < '2021-02-18')
AND site.campground_id = 1
LIMIT 5;

select utilities
from site
where utilities = 'false'
;

select reservation.site_id, from_date, to_date
from reservation
inner join site
on site.site_id = reservation.site_id
inner join campground
on site.campground_id = campground.campground_id
where campground.campground_id = 1
order by reservation.site_id, from_date, to_date
;

select *
from reservation
where site_id in (select site_id 
from site
where site.campground_id in (select campground_id from campground where park_id in 
(select park_id from park where park_id = 1)))
and site_id NOT in (select site_id from reservation where from_date <= '2021-05-03' and to_date > '2021-05-04'
or from_date < '2021-05-03' and to_date >= '2021-05-04'
or from_date > '2021-05-03' and to_date < '2021-05-04')
order by create_date, from_date, to_date
;

SELECT *, (reservation.to_date  - reservation.from_date)  * daily_fee as cost_of_stay 
FROM site
inner join campground
on site.campground_id = campground.campground_id
inner join reservation
on site.site_id = reservation.site_id
WHERE site.campground_id = 1
AND site.site_id NOT in (
SELECT site_id
FROM reservation
WHERE from_date <= '2021-03-01' AND to_date > '2021-03-10'
OR from_date < '2021-03-01' AND to_date >= '2021-03-10'
OR from_date > '2021-03-01' AND to_date < '2021-03-10'
)
--LIMIT 5
;

select (reservation.to_date  - reservation.from_date)  * daily_fee as cost_of_stay 
from reservation
inner join
site 
on reservation.site_id = site.site_id
inner join
campground
on site.campground_id = campground.campground_id
where reservation_id = ?
;
