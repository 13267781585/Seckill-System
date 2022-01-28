local apiMethodName = "seckill:api:limiter:" .. KEYS[1]
local nowTokenNumKey = apiMethodName .. ":nowTokenNumKey"
local maxTokenNumKey = apiMethodName .. ":maxTokenNumKey"
local intervalTimesKey = apiMethodName .. ":intervalTimesKey"
local nextFreeTokenKey = apiMethodName .. ":nextFreeTokenKey"

local nowTokenValue = tonumber(redis.call('get',nowTokenNumKey))
local maxTokenNumValue = tonumber(redis.call('get',maxTokenNumKey))
local intervalTimesValue = tonumber(redis.call('get',intervalTimesKey))
local nextFreeTokenValue = tonumber(redis.call('get',nextFreeTokenKey))
local nowTime = tonumber(ARGV[1])

if  nowTime > nextFreeTokenValue then
	local tokenNum = (nowTime - nextFreeTokenValue) * intervalTimesValue + nowTokenValue
	if tokenNum >= maxTokenNumValue then
	redis.call('set',nowTokenNumKey,maxTokenNumValue - 1)
	else 
	redis.call('set',nowTokenNumKey,tokenNum - 1)
	end
	redis.call('set',nextFreeTokenKey,nowTime)
	return 1
end

if nowTokenValue > 0 then
	redis.call('set',nowTokenNumKey,nowTokenValue - 1)
	return 1;
end
return 0

