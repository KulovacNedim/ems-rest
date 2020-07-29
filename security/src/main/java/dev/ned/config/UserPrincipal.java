package dev.ned.config;

public class UserPrincipal
//        implements
//        UserDetails
{
//    private User user;
//
//    public UserPrincipal(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        this.user.getRoles().forEach(role -> {
//            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRoleName());
//            authorities.add(authority);
//        });
//
//        // do same for roles (without of ROLE_)
//
//
//        return authorities;
//    }
//
//    public Long getId() {
//        return this.user.getId();
//    }
//
//    @Override
//    public String getPassword() {
//        return this.user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.user.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.user.isActive();
//    }
//
//    @Override
//    public String toString() {
//        return "UserPrincipal{" +
//                "user=" + user.toString() +
//                '}';
//    }
}