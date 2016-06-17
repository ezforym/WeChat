package com.ysg.menuUtils;

/**
 * 菜单管理器类
 * 
 * @author 杨建冬
 * @date 2013-09-10
 */
public class MenuManager {
	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wxe1c98a8f96ccb726";
		// 第三方用户唯一凭证密钥
		String appSecret = "ad2436f9750a325b14f0a08d3009c42f";

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        System.out.println(at.getToken()); 
		if (null != at) {
			// 调用接口创建菜单 
			int result = WeixinUtil.createMenu(getMenu(),at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		/*ViewButton btn11 = new ViewButton();  
        btn11.setName("美白");  
        btn11.setType("view");  
        btn11.setUrl("http://1.yizelover.sinaapp.com/bueatiful/images.php"); */
		CommonButton btn11 = new CommonButton();
		btn11.setName("美白");
		btn11.setType("click");
		btn11.setKey("11");
		
		CommonButton btn12 = new CommonButton();
		btn12.setName("补水");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("祛斑");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn14 = new CommonButton();
		btn14.setName("所有商品");
		btn14.setType("click");
		btn14.setKey("14");
		
		
		
		CommonButton btn21 = new CommonButton();
		btn21.setName("美即");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("戴莱美");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("蓝莓");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("奇迹");
		btn24.setType("click");
		btn24.setKey("24");

		CommonButton btn25 = new CommonButton();
		btn25.setName("韩后");
		btn25.setType("click");
		btn25.setKey("25");

		CommonButton btn31 = new CommonButton();
		btn31.setName("订单查询");
		btn31.setType("click");
		btn31.setKey("31");

		CommonButton btn32 = new CommonButton();
		btn32.setName("地址管理");
		btn32.setType("click");
		btn32.setKey("32");

		CommonButton btn33 = new CommonButton();
		btn33.setName("联系卖家");
		btn33.setType("click");
		btn33.setKey("33");
		/*ViewButton btn33 = new ViewButton();  
        btn33.setName("联系卖家");  
        btn33.setType("view");  
        btn33.setUrl("http://2.yizelover.sinaapp.com/mingpian.php"); */
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("功效分类");
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14,  });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("品牌分类");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("我的信息");
		mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });

		/**
		 * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
