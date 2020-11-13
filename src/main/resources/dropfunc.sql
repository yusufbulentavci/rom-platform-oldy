CREATE OR REPLACE FUNCTION dropfunction(p_schema text, p_func text)
  RETURNS text AS
$BODY$
declare
    funcrow record;
    numfunctions smallint := 0;
    numparameters int;
    i int;
    paramtext text;
begin
for funcrow in select proargtypes from pg_proc p,pg_namespace ns 
	where proname = p_func and p_schema=nspname and  p.pronamespace=ns.oid loop

    --for some reason array_upper is off by one for the oidvector type, hence the +1
    numparameters = array_upper(funcrow.proargtypes, 1) + 1;

    i = 0;
    paramtext = '';

    loop
        if i < numparameters then
            if i > 0 then
                paramtext = paramtext || ', ';
            end if;
            paramtext = paramtext || (select nspname||'.'||typname from pg_type ty,pg_namespace ns where ns.oid=typnamespace and ty.oid = funcrow.proargtypes[i]);
            i = i + 1;
        else
            exit;
        end if;
    end loop;

 --   raise notice '%','drop function if exists ' || p_schema || '.' || p_func || '(' || paramtext || ') cascade;';
    execute 'drop function if exists ' || p_schema || '.' || p_func || '(' || paramtext || ') cascade;';
    numfunctions = numfunctions + 1;

end loop;

return 'dropped ' || numfunctions || ' functions';
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

create or replace function assertcompexists(p_which text, p_schema text, p_name text)
  returns boolean AS
$$
declare
    funcrow record;
    numfunctions smallint := 0;
    numparameters int;
    i int;
    paramtext text;
	v_found boolean;
begin

if p_which='function' then

	select true into v_found 
		from pg_proc p,pg_namespace ns 
		where proname = p_name and nspname=p_schema and  p.pronamespace=ns.oid;
elsif p_which='table' then

	select true into v_found
		from pg_tables p 
		where tablename = p_name and schemaname=p_schema;

elsif p_which='schema' then
	select true into v_found
		from pg_namespace
		where nspname=p_schema;

elsif p_which='index' then
	select true into v_found 
		from pg_indexes 
		where schemaname=p_schema and indexname=p_name;

elsif p_which='trigger' then
	select true into v_found 
		from pg_trigger 
		where tgname=p_name;

elsif p_which='trigger' then
	select true into v_found 
		from pg_trigger 
		where tgname=p_name;

elsif p_which='type' then
	select true into v_found 
		from pg_type p,pg_namespace ns 
		where typname = p_name and nspname=p_schema and  p.typnamespace=ns.oid;

elsif p_which='aggregate' then
	select true into v_found 
		from pg_aggregate 
		where aggfnoid=(p_schema||'.'||p_name)::regproc;

elsif p_which='data' then
	return true;


elsif p_which='sequence' then
	return true;

end if;

if v_found is null or not v_found then
	raise exception 'Comp not found type:% schema:% name:%',p_which, p_schema, p_name;
end if;

return true;
end;
$$ language 'plpgsql';



