local apiMethodName = "seckill:api:limiter:" .. KEYS[1]
local nowTokenNumKey = apiMethodName .. ":nowTokenNumKey"
local maxTokenNumKey = apiMethodName .. ":maxTokenNumKey"
local intervalTimesKey = apiMethodName .. ":intervalTimesKey"
local nextFreeTokenKey = apiMethodName .. ":nextFreeTokenKey"
redis.call('set',nowTokenNumKey,ARGV[1])
redis.call('set',maxTokenNumKey,ARGV[2])
redis.call('set',intervalTimesKey,ARGV[3])
redis.call('set',nextFreeTokenKey,ARGV[4])