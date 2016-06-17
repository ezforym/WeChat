package com.ysg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import ysg.weixinutils.response.Article;
import ysg.weixinutils.response.NewsMessage;
import ysg.weixinutils.response.TextMessage;

/**
 * 核心服务类
 * 
 * @author 杨建冬
 * @date 2013-09-10
 */
public class CoreService
{
	/**
	 * 处理微信发来的请求 换行符仍然是"\n"
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request)
	{
		String respMessage = null;
		boolean temp1 = false;
		boolean temp2 = false;
		boolean temp3 = false;
		boolean temp4 = false;
		boolean temp5 = false;
		try
		{
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			System.out.println("fromUserName is :" + fromUserName);
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			System.out.println("toUserName is :" + toUserName);
			String msgType = requestMap.get("MsgType");
			// 回复文本消息
			System.out.println("MsgType is :" + msgType);
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			// 两种获取整形时间的方法。
			// 获取到的结果表示当时时间距离1970年1月1日0时0分0秒0毫秒的毫秒数。公众平台api中消息创建时间CreateTime，它表示1970年1月1日0时0分0秒至消息创建时所间隔的秒数，注意是间隔的秒数，不是毫秒数！
			// long longTime1 = System.currentTimeMillis();
			// long longTime2 = new java.util.Date().getTime();
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT))
			{
				// 文本消息内容
				String content = requestMap.get("Content");
				respContent = "您发送的是文本消息！\n谢谢！您发送的内容为：" + content;
				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);
				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				temp1 = "1".equals(content);
				temp2 = "2".equals(content);
				temp3 = "3".equals(content);
				temp4 = "4".equals(content);
				temp5 = "5".equals(content);
				if (temp1)
				{
					Article article = new Article();
					article.setTitle("nice girl in the adress");
					article.setDescription("press is ok");
					article.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article.setUrl(
							"http://www.baidu.com");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}

				// 单图文消息---不含图片
				else if (temp2)
				{
					Article article = new Article();
					article.setTitle("微信公众帐号开发教程");
					// 图文消息中可以使用QQ表情、符号表情
					article.setDescription("单图文消息描述" + EmojiUtils.emoji(0x1F6B9)
							+ "，单图文消息描述\n\n单图文消息描述\n\n单图文消息描述");
					// 将图片置为空
					article.setPicUrl("");
					article.setUrl("http://www.baidu.com");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if (temp3)
				{
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程\n引言");
					article1.setDescription("");
					article1.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article1.setUrl("http://www.baidu.com");

					Article article2 = new Article();
					article2.setTitle("第2篇\n微信公众帐号的类型");
					article2.setDescription("");
					article2.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article2.setUrl("http://www.baidu.com");

					Article article3 = new Article();
					article3.setTitle("第3篇\n开发模式启用及接口配置");
					article3.setDescription("");
					article3.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article3.setUrl("http://www.baidu.com");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if (temp4)
				{
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程Java版");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("");
					article1.setUrl("http://www.baidu.com");

					Article article2 = new Article();
					article2.setTitle("第4篇\n消息及消息处理工具的封装");
					article2.setDescription("");
					article2.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article2.setUrl("http://www.baidu.com");

					Article article3 = new Article();
					article3.setTitle("第5篇\n各种消息的接收与响应");
					article3.setDescription("");
					article3.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article3.setUrl("http://www.baidu.com");

					Article article4 = new Article();
					article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
					article4.setDescription("");
					article4.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article4.setUrl("http://www.baidu.com");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if (temp5)
				{
					Article article1 = new Article();
					article1.setTitle("第7篇\n文本消息中换行符的使用");
					article1.setDescription("");
					article1.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article1.setUrl("http://www.baidu.com");

					Article article2 = new Article();
					article2.setTitle("第8篇\n文本消息中使用网页超链接");
					article2.setDescription("");
					article2.setPicUrl(
							"http://c.zpdteck.com/engineers/0b868deab12d3063eb7078097f27b617.png");
					article2.setUrl("http://www.baidu.com");

					Article article3 = new Article();
					article3.setTitle(
							"如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("");
					article3.setUrl("http://www.baidu.com");
					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE))
			{
				respContent = "您发送的是图片消息！/微笑";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION))
			{
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK))
			{
				respContent = "您发送的是<a href=\"http://www.24hs.cn/\">链接</a>消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE))
			{
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
			{
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE))
				{
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE))
				{
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK))
				{
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("11"))
					{
						respContent = "天气预报菜单项被点击！";
					} else if (eventKey.equals("12"))
					{
						respContent = "公交查询菜单项被点击！";
					} else if (eventKey.equals("13"))
					{
						respContent = "周边搜索菜单项被点击！";
					} else if (eventKey.equals("14"))
					{
						respContent = "历史上的今天菜单项被点击！";
					} else if (eventKey.equals("21"))
					{
						respContent = "歌曲点播菜单项被点击！";
					} else if (eventKey.equals("22"))
					{
						respContent = "经典游戏菜单项被点击！";
					} else if (eventKey.equals("23"))
					{
						respContent = "美女电台菜单项被点击！";
					} else if (eventKey.equals("24"))
					{
						respContent = "人脸识别菜单项被点击！";
					} else if (eventKey.equals("25"))
					{
						respContent = "聊天唠嗑菜单项被点击！";
					} else if (eventKey.equals("31"))
					{
						respContent = "Q友圈菜单项被点击！";
					} else if (eventKey.equals("32"))
					{
						respContent = "电影排行榜菜单项被点击！";
					} else if (eventKey.equals("33"))
					{
						respContent = "幽默笑话菜单项被点击！";
					}
				}
			}
			textMessage.setContent(respContent);
			if (temp1 || temp2 || temp3 || temp4 || temp5)
				;
			else
				respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return respMessage;
	}
}