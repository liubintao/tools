package com.robust.online.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @Description:
 * @Author: robust
 * @CreateDate: 2019/1/8 10:16
 * @Version: 1.0
 */
public class TableNameTransfer {
    private static final HashMap<String, String> tableMap = new HashMap<>();
//    private static final String LOCATION = "C:\\Users\\bintao\\Desktop\\xtyh20181229\\xtyh";

    static {
        tableMap.put("个人信息表", "﻿Pcif");
        tableMap.put("个人用户表", "Puser");
        tableMap.put("个人账号表", "PAccount");
        tableMap.put("个人客户级规则表", "PCifRule");
        tableMap.put("个人客户级限额表", "PCifLimit");
        tableMap.put("个人收款人信息表", "PPayeeInfo");
        tableMap.put("对私转账交易流水表", "PJnlTransfer");
        tableMap.put("对私批量转账交易明细表", "PBatchTransfer");
        tableMap.put("个人用户访问表", "PJnlAccess");
        tableMap.put("个人定时交易具体信息表", "PTaskDetail");
        tableMap.put("个人日志表", "PJnl");
        tableMap.put("个人定时计划表", "PTaskRule");
        tableMap.put("个人账号规则表", "PacRule");
        tableMap.put("个人账户限额记录", "PAcLimit");
        tableMap.put("电子联行信息表", "APSRtgsnode");
        tableMap.put("缴费信息维护表", "apscommrecords");
        tableMap.put("个人协议账号表", "PAccountProtocol");
        tableMap.put("银行个人信息表", "PBankCif");
        tableMap.put("个人网银处理交易队列表", "PRouterQueue");
        tableMap.put("个人用户账户对应表", "PUserAccount");
        tableMap.put("个人用户证书信息表", "PUserCert");
        tableMap.put("个人用户级规则表", "PUserRule");
        tableMap.put("个人客户级限额历史表", "PCifLimit_his");
        tableMap.put("个人日志历史表", "PJnl_his");
        tableMap.put("个人用户访问历史表", "PJnlAccess_his");
        tableMap.put("个人账户限额历史记录", "PAcLimit_his");
        tableMap.put("客户信息表", "Ecif");
        tableMap.put("银行企业信息表", "EBankCif");
        tableMap.put("企业用户银行角色表", "EUserRole");
        tableMap.put("企业用户表", "Euser");
        tableMap.put("用户证书信息表", "EUserCert");
        tableMap.put("企业账户表", "Eaccount");
        tableMap.put("企业交易授权目标", "EAuthTarget");
        tableMap.put("企业收款人信息表", "EPayeeInfo");
        tableMap.put("企业账户级规则表", "EAcRule");
        tableMap.put("企业账户限额记录", "EAcLimit");
        tableMap.put("转账交易流水表", "EJnlTransfer");
        tableMap.put("企业账户属性表", "EAcArg");
        tableMap.put("协议账号列表", "EAccountProtocol");
        tableMap.put("交易审核明细表", "EAuthDetail");
        tableMap.put("交易审核队列表", "EAuthQueue");
        tableMap.put("企业授权目标授权配置", "EAuthTargetAuthCfg");
        tableMap.put("批量转账交易明细表", "EBatchTransfer");
        tableMap.put("企业客户级限额表", "ECifLimit");
        tableMap.put("企业客户角色表", "ECifRole");
        tableMap.put("企业规则表", "ECifRule");
        tableMap.put("企业集团上下级关系表", "EGroupRelation");
        tableMap.put("企业日志表", "Ejnl");
        tableMap.put("企业用户访问表", "EJnlAccess");
        tableMap.put("企业用户打印密码表", "EPasswordPrint");
        tableMap.put("代发工资批次表", "EPayeeSalary");
        tableMap.put("企业网银处理交易队列表", "ERouterQueue");
        tableMap.put("定时交易具体信息表", "ETaskDetail");
        tableMap.put("定时计划表", "ETaskRule");
        tableMap.put("企业用户账户对应表", "EUserAccount");
        tableMap.put("企业用户组", "EUserGroup");
        tableMap.put("企业用户组与用户关系表", "EUserGroupUser");
        tableMap.put("批量转账交易明细历史表", "EBatchTransfer_his");
        tableMap.put("企业客户级限额历史表", "ECifLimit_his");
        tableMap.put("企业日志历史表", "Ejnl_his");
        tableMap.put("企业用户访问历史表", "EJnlAccess_his");
        tableMap.put("企业账户限额记录历史表", "EAcLimit_his");
        tableMap.put("转账交易流水历史表", "EJnlTransfer_his");
        tableMap.put("银行部门信息表", "BankRole");
        tableMap.put("银行机构表", "BankDept");
        tableMap.put("银行机构角色关系表", "BankDeptRole");
        tableMap.put("部门信息表", "BankDiv");
        tableMap.put("部门角色组信息表", "BankDivRole");
        tableMap.put("银行操作员信息表", "Mteller");
        tableMap.put("柜员角色表", "MtellerRole");
        tableMap.put("代缴费业务信息表", "APSBusiness");
        tableMap.put("缴费业务表", "APSunit");
        tableMap.put("移动银行规则表", "BankRule");
        tableMap.put("银行系统节假日表", "BankHoliday");
        tableMap.put("银行公告表", "BankNotice");
        tableMap.put("银行规则与产品组关系表", "BankRolePGp");
        tableMap.put("机构规则表", "DeptRule");
        tableMap.put("后台交易审核队列表", "MauthQueue");
        tableMap.put("后台日志表", "Mjnl");
        tableMap.put("管理用户访问表", "MjnlAccess");
        tableMap.put("图片信息表", "SysImage");
        tableMap.put("协议信息表", "SysProtocol");
        tableMap.put("防伪信息表", "TextAntifake");
        tableMap.put("用户留言表", "UserNote");
        tableMap.put("管理用户访问历史表", "MjnlAccess_his");
    }

    public static void process(Path path) throws IOException {
        Files.list(path).forEach(p -> {
            if (Files.isDirectory(p)) {
                try {
                    process(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String fileName = p.getFileName().toString();
                String tableCN = fileName.substring(0, fileName.length() - 4);
                if (tableMap.containsKey(tableCN)) {
                    Path targetPath = p.getParent().resolve(tableMap.get(tableCN) + fileName.substring(fileName.length() - 4));
                    try {
                        Files.move(p, targetPath);
                        System.out.println(fileName + " --> " + targetPath.getFileName().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        process(path);
    }
}
