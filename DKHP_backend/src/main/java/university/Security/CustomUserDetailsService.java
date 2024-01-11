package university.Security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import university.Exception.RequestException;
import university.Model.User;
import university.Repository.UserRepo;
import university.Util.InfoChecking;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private InfoChecking infoChecking;
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		int type=infoChecking.checkUsernameType(username);
		User u;
		if(type==1) u=userRepo.findByUserId(username).orElseThrow(()->new BadCredentialsException("UserId you specified does not exists!"));
		else if(type==2) u=userRepo.findByEmail(username).orElseThrow(()->new BadCredentialsException("Email you specified does not exists!"));
		else throw new BadCredentialsException("Unable to validate the email/userId you entered");
		return new CustomUserDetails(u);
	}
	public CustomUserDetails loadById(String id) {
		User u=userRepo.findById(Integer.valueOf(id)).orElseThrow(()->new RequestException("UserId you specified does not exists!"));
		return new CustomUserDetails(u);
	}
}
