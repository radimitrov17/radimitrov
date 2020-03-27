select *
from site
where campground_id = 1 and site_id not in(select site.site_id from reservation
join site on site.site_id = reservation.site_id
join campground on campground.campground_id = site.campground_id
where site.campground_id = 1 and (('2020-02-15' between reservation.from_date and reservation.to_date)
or ('2020-02-20' between reservation.from_date and reservation.to_date)
or (reservation.from_date between '2020-02-15' and '2020-02-20')
or (reservation.to_date between '2020-02-15' and '2020-02-20')))