1.实现了httpBasic鉴权
2.实现了formLogin鉴权
（①认证鉴权信息写死在代码中，加载到内存中②用户信息和权限写死在代码中，加载到内存中
③实现退出登录功能，并自定义成功登出后的逻辑④使用自定义的成功认证后的逻辑⑤使用自定义的失败后的逻辑
⑥session管理功能，同一账号密码最大登录数，用户抢占登录逻辑，session过期后自定义的逻辑等）
3.使用Kaptcha实现了登录验证码功能，并设置过滤器，将验证码过滤器放到账号密码前面执行
4.remember-me自动登录功能在username-server模块中实现