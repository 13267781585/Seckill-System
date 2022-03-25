-- 限流初始化
--[[
	KEYS[1] 接口的名称
	ARGV[1]  当前令牌数
	ARGV[2]  最大存储令牌数
	ARGV[3] 时间间隔
	ARGV[4] 上次请求时间
	key需要加上{} 保证数据在一个节点
	lua脚本只能操作一个节点上的数据
  ]]
local apiMethodName = "seckill:api:limiter:" .. KEYS[1]
local nowTokenNumKey = apiMethodName .. ":nowTokenNumKey"
local maxTokenNumKey = apiMethodName .. ":maxTokenNumKey"
local intervalTimesKey = apiMethodName .. ":intervalTimesKey"
local nextFreeTokenKey = apiMethodName .. ":nextFreeTokenKey"
redis.call('set',nowTokenNumKey,ARGV[1])  -- 当前存储令牌数
redis.call('set',maxTokenNumKey,ARGV[2])  -- 最大存储令牌数
redis.call('set',intervalTimesKey,ARGV[3])  -- 添加令牌时间间隔
redis.call('set',nextFreeTokenKey,ARGV[4])  -- 上一次请求令牌时间