package com.iresearch.controller;


import com.iresearch.entity.User;
import com.iresearch.service.IUserService;
import com.iresearch.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/iresearch/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    /**
     *  获取全部 user
     */
    @RequiresRoles(value = {"admin"})
    @GetMapping
    public List<UserVO> getUsers(HttpServletResponse response){
        final List<User> users = iUserService.list();
        response.setStatus(HttpStatus.OK.value());
        return iUserService.userList2userVOList(users);
    }

    /**
     * 添加用户
     */

    @PostMapping
    public void addUser(String name, String password, String nickName, int type,
                        HttpServletResponse response){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setNickname(nickName);
        user.setUserType(type);
        user.setCreateTime(LocalDateTime.now());

        final boolean save = iUserService.save(user);
        if (!save){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else{
            response.setStatus(HttpStatus.OK.value());
        }
    }

    /**
     * 修改用户
     */
    @PutMapping
    public void updateUser(int id, String name, String password, String nickName,
                        HttpServletResponse response){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setNickname(nickName);
        // 默认 都为1
        user.setUserType(1);
        user.setUpdateTime(LocalDateTime.now());

        final boolean save = iUserService.updateById(user);
        if (!save){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else{
            response.setStatus(HttpStatus.OK.value());
        }
    }

    /**
     * 修改昵称 (注意 shiro PathVariable 传递中文)
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @PostMapping("/nickname")
    public void updateNickname(String nickname,
                               HttpServletResponse response){
        final Subject subject = SecurityUtils.getSubject();
        final String name = (String) subject.getPrincipal();
        final User user = iUserService.getUserByName(name);
        user.setNickname(nickname);
        iUserService.updateById(user);
        response.setStatus(HttpStatus.OK.value());
    }
    /**
     * 修改密码
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @PostMapping("/{oldpsw}/{newpsw}")
    public void updatePassword(@PathVariable String oldpsw, @PathVariable String newpsw,
                               HttpServletResponse response){
        final Subject subject = SecurityUtils.getSubject();
        final String name = (String) subject.getPrincipal();

        final User user = iUserService.getUserByName(name);
        if (oldpsw.equals(user.getPassword())){
            user.setPassword(newpsw);
            iUserService.updateById(user);
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 管理员 根据 id 修改密码
     */
    @RequiresRoles({"admin"})
    @PostMapping("/id/{userid}/{newpsw}")
    public void updatePasswordById(@PathVariable String userid, @PathVariable String newpsw,
                                   HttpServletResponse response){
        final User user = iUserService.getById(userid);
        user.setPassword(newpsw);
        iUserService.updateById(user);
        response.setStatus(HttpStatus.OK.value());

    }

    // login 与 logout 重复

    @PostMapping("/login")
    public void login(String name,String password,
                      HttpServletResponse response){

        final Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(name,password));
        response.setStatus(HttpStatus.OK.value());
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response){

        final Subject subject = SecurityUtils.getSubject();
        subject.logout();
        response.setStatus(HttpStatus.OK.value());
    }

}

