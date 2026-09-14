package com.waimai.view;

import com.waimai.entity.Cart;
import com.waimai.entity.Dish;
import com.waimai.entity.Merchant;
import com.waimai.entity.Order;
import com.waimai.entity.User;
import com.waimai.service.CartService;
import com.waimai.service.DishService;
import com.waimai.service.MerchantService;
import com.waimai.service.OrderService;
import com.waimai.service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * 控制台界面：负责「接收输入 + 显示输出」。
 * 只调用 service，不碰数据库，也不写业务规则。
 */
public class ConsoleUI {

    private final UserService userService = new UserService();
    private final MerchantService merchantService = new MerchantService();
    private final DishService dishService = new DishService();
    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();

    private final Scanner scanner = new Scanner(System.in);
    private User currentUser; // 当前登录的用户，null 表示没登录

    public void start() {
        while (true) {
            System.out.println("\n===== 外卖点餐系统 =====");
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            System.out.println("3. 退出");
            System.out.print("请选择：");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("再见！");
                    return;
                default:
                    System.out.println("输入有误，请重新选择");
            }
            // 登录成功后进入主菜单
            if (currentUser != null) {
                mainMenu();
            }
        }
    }

    private void register() {
        System.out.print("用户名：");
        String username = scanner.nextLine();
        System.out.print("密码：");
        String password = scanner.nextLine();
        System.out.print("手机号：");
        String phone = scanner.nextLine();
        System.out.print("地址：");
        String address = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);

        try {
            userService.register(user);
            System.out.println("注册成功，请登录");
        } catch (Exception e) {
            System.out.println("注册失败：" + e.getMessage());
        }
    }

    private void login() {
        System.out.print("用户名：");
        String username = scanner.nextLine();
        System.out.print("密码：");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user == null) {
            System.out.println("登录失败：用户名或密码错误");
        } else {
            currentUser = user;
            System.out.println("欢迎，" + user.getUsername() + "！");
        }
    }

    private void mainMenu() {
        while (currentUser != null) {
            System.out.println("\n===== 主菜单（" + currentUser.getUsername() + "） =====");
            System.out.println("1. 浏览商家");
            System.out.println("2. 查看购物车");
            System.out.println("3. 下单");
            System.out.println("4. 我的订单");
            System.out.println("5. 退出登录");
            System.out.print("请选择：");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    browseMerchants();
                    break;
                case "2":
                    viewCart();
                    break;
                case "3":
                    placeOrder();
                    break;
                case "4":
                    viewOrders();
                    break;
                case "5":
                    currentUser = null;
                    System.out.println("已退出登录");
                    break;
                default:
                    System.out.println("输入有误，请重新选择");
            }
        }
    }

    private void browseMerchants() {
        List<Merchant> merchants = merchantService.listMerchants();
        System.out.println("\n===== 商家列表 =====");
        for (int i = 0; i < merchants.size(); i++) {
            Merchant m = merchants.get(i);
            System.out.println((i + 1) + ". " + m.getName() + "（" + m.getCategory() + "）" + m.getAddress());
        }
        System.out.print("输入商家编号查看菜品（0 返回）：");
        int idx = readInt();
        if (idx > 0 && idx <= merchants.size()) {
            browseDishes(merchants.get(idx - 1));
        }
    }

    private void browseDishes(Merchant merchant) {
        List<Dish> dishes = dishService.listByMerchant(merchant.getId());
        System.out.println("\n===== " + merchant.getName() + " 的菜品 =====");
        for (int i = 0; i < dishes.size(); i++) {
            Dish d = dishes.get(i);
            System.out.println((i + 1) + ". " + d.getName() + "  ¥" + d.getPrice() + "  库存:" + d.getStock());
        }
        System.out.print("输入菜品编号加入购物车（0 返回）：");
        int idx = readInt();
        if (idx > 0 && idx <= dishes.size()) {
            Dish d = dishes.get(idx - 1);
            System.out.print("数量：");
            int qty = readInt();
            if (qty > 0) {
                cartService.add(currentUser.getId(), d.getId(), qty);
                System.out.println("已加入购物车：" + d.getName() + " x" + qty);
            } else {
                System.out.println("数量必须大于 0");
            }
        }
    }

    private void viewCart() {
        List<Cart> items = cartService.listCart(currentUser.getId());
        System.out.println("\n===== 我的购物车 =====");
        if (items.isEmpty()) {
            System.out.println("购物车是空的");
            return;
        }
        for (Cart item : items) {
            Dish dish = dishService.getDish(item.getDishId());
            System.out.println(item.getId() + ". " + dish.getName() + "  ¥" + dish.getPrice() + "  x" + item.getQuantity());
        }
    }

    private void placeOrder() {
        try {
            orderService.placeOrder(currentUser.getId());
            System.out.println("下单成功！");
        } catch (Exception e) {
            System.out.println("下单失败：" + e.getMessage());
        }
    }

    private void viewOrders() {
        List<Order> orders = orderService.listMyOrders(currentUser.getId());
        System.out.println("\n===== 我的订单 =====");
        if (orders.isEmpty()) {
            System.out.println("还没有订单");
            return;
        }
        for (Order o : orders) {
            System.out.println("订单号:" + o.getId()
                    + "  总价:¥" + o.getTotalPrice()
                    + "  状态:" + o.getStatus()
                    + "  时间:" + o.getCreateTime());
        }
    }

    /**
     * 安全地读一个整数。输入不是数字时返回 -1，而不是让程序崩溃。
     * 这里用到的 try-catch 就是「异常处理」，是你下一步要学的内容。
     */
    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
