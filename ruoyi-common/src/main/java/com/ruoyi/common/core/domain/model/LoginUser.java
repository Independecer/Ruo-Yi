package com.ruoyi.common.core.domain.model;

import java.util.Collection;
import java.util.Set;

import com.ruoyi.common.core.domain.IStoreRole;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    private Collection<IStoreRole> storeRoles;

    /**
     * 用户信息
     */
    private SysUser user;

    private WxUser wxUser;

    public Long getUserId() {
        if(user == null){
            return null;
        }
        return user.getUserId();
    }

    public Long getDeptId() {
        if(user == null){
            return  null;
        }
        return user.getDeptId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginUser() {
    }

    public LoginUser(SysUser user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(Long userId, Long deptId, SysUser user, Set<String> permissions) {
        this.userId = userId;
        this.deptId = deptId;
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(SysUser sysUser, WxUser user, Set<String> permissions, Collection<IStoreRole> storeRoles) {
        if(sysUser != null){
            this.userId = sysUser.getUserId();
            this.deptId = sysUser.getDeptId();
            this.user = sysUser;
        }
        this.wxUser = user;
        this.storeRoles = storeRoles;
        this.permissions = permissions;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (user != null )
            return user.getUserName();
        else
            return getWxUser().getOpenId();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Collection<IStoreRole> getStoreRoles(){
        return storeRoles;
    }

    public void setStoreRoles(Collection<IStoreRole> storeRoles){
        this.storeRoles = storeRoles;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public WxUser getWxUser() {
        return wxUser;
    }

    public void setWxUser(WxUser wxUser){
        this.wxUser = wxUser;
    }

    public String getOpenId(){
        if(wxUser == null){
            return null;
        }
        return wxUser.getOpenId();
    }

    public String getUnionId(){
        if(wxUser == null){
            return null;
        }
        return wxUser.getUnionId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
