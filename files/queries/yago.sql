SELECT  y1.id as rel_id, y1.subject as person1, y1.predicate, y1.object as person2
FROM YAGOFACTS y1
WHERE y1.predicate = '<isMarriedTo>'
LIMIT 2
offset 1000000

select * from yagofacts where subject = '<Rob_Diament>'

select predicate, value from yagofacts
where subject = '<id_18hc2h7_16x_1722kjb>' and 
(predicate = '<occursSince>' or predicate = '<occursUntil>')

select * from yagofacts
where subject = '<id_1hiw3m2_16x_1al72jl>'
select * from yagofacts
where object = '<id_1hiw3m2_16x_1al72jl>'

WHERE PREDICATE = 'wasBornIn'
WHERE PREDICATE LIKE '%BORN%'