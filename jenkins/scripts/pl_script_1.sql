create or replace function test_function(param in number) return number is
  Result number;
begin
  Result := param * 3;
  return(Result);
end test_function;