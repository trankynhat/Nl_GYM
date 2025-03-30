CREATE OR REPLACE VIEW coach_schedule_view AS
SELECT 
    cl.id AS class_id,
    ct.name AS class_name,
    ct.description AS class_description,
    ct.default_duration AS duration_minutes,
    ct.max_participants,
    u.full_name AS coach_name,
    u.email AS coach_email,
    cl.start_date,
    GROUP_CONCAT(DISTINCT cs.day_of_week ORDER BY cs.day_of_week SEPARATOR ', ') AS days_of_week,
    GROUP_CONCAT(DISTINCT cs.start_time ORDER BY cs.start_time SEPARATOR ', ') AS start_times
FROM classes cl
JOIN class_templates ct ON cl.template_id = ct.id
JOIN coaches co ON cl.coach_id = co.id
JOIN users u ON co.user_id = u.id
LEFT JOIN class_schedules cs ON cl.id = cs.class_id
GROUP BY cl.id, ct.name, ct.description, ct.default_duration, ct.max_participants, u.full_name, u.email, cl.start_date;
