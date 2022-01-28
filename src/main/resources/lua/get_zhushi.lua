--[[
	获取令牌
	KEYS[1] 接口的名称
	ARGV[1] 当前时间戳 
	return 成功 1 失败 0
	redis 不允许使用全局变量
  ]]

local apiMethodName = "seckill:api:limiter:" .. KEYS[1]
local nowTokenNumKey = apiMethodName .. ":nowTokenNumKey"
local maxTokenNumKey = apiMethodName .. ":maxTokenNumKey"
local intervalTimesKey = apiMethodName .. ":intervalTimesKey"
local nextFreeTokenKey = apiMethodName .. ":nextFreeTokenKey"
-- 获取参数
local nowTokenValue = tonumber(redis.call('get',nowTokenNumKey))  -- 当前存储令牌数
local maxTokenNumValue = tonumber(redis.call('get',maxTokenNumKey))  -- 最大存储令牌数
local intervalTimesValue = tonumber(redis.call('get',intervalTimesKey))  -- 添加令牌时间间隔
local nextFreeTokenValue = tonumber(redis.call('get',nextFreeTokenKey))  -- 上一次请求令牌时间
local nowTime = tonumber(ARGV[1])

-- 刷新令牌数量并更新令牌数目和时间戳返回
if  nowTime > nextFreeTokenValue then
	-- 计算现在令牌数量
	local tokenNum = (nowTime - nextFreeTokenValue) * intervalTimesValue + nowTokenValue
	-- 判断令牌数量是否溢出
	if tokenNum >= maxTokenNumValue then 
	-- 先去令牌数量  并更新
	redis.call('set',nowTokenNumKey,maxTokenNumValue - 1)
	else 
	redis.call('set',nowTokenNumKey,tokenNum - 1)
	end
	-- 更新时间戳
	redis.call('set',nextFreeTokenKey,nowTime)
	return 1
end

-- 判断是否有令牌
if nowTokenValue > 0 then
	redis.call('set',nowTokenNumKey,nowTokenValue - 1)
	return 1;
end
return 0

