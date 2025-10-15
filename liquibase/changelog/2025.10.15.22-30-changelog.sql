--changeset vpolkhovsky:create-function-get_resource_consumption
CREATE OR REPLACE FUNCTION billing.get_resource_consumption(
    time_interval TEXT DEFAULT 'hour'
)
RETURNS TABLE (
    function_id uuid,
    type varchar,
    time_period timestamp,
    measurement_count bigint,
    avg_value float8,
    sum_value float8,
    max_value float8,
    min_value float8
) AS $$
BEGIN
RETURN QUERY EXECUTE format('
    SELECT
        function_id,
        type,
        DATE_TRUNC(%L, created_at) as time_period,
        COUNT(*) as measurement_count,
        AVG(value) as avg_value,
        SUM(value) as sum_value,
        MAX(value) as max_value,
        MIN(value) as min_value
    FROM billing.metrics
    GROUP BY function_id, type, DATE_TRUNC(%L, created_at)
    ORDER BY function_id, type, time_period',
    time_interval, time_interval);
END;
$$
LANGUAGE plpgsql;