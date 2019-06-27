package com.mycompany.climbworksscheduler.service;

import com.mycompany.climbworksscheduler.data.EmployeeRepo;
import com.mycompany.climbworksscheduler.entity.Employee;
import com.mycompany.climbworksscheduler.entity.Role;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepo empRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee e = empRepo.findByUsername(username).orElse(null);
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        if (e.getPassword().equals("emp1") || e.getPassword().equals("emp2") || e.getPassword().equals("emp3") || e.getPassword().equals("emp4") || e.getPassword().equals("emp5") || e.getPassword().equals("emp6") || e.getPassword().equals("emp7") || e.getPassword().equals("emp8") || e.getPassword().equals("emp9") || e.getPassword().equals("emp10") || e.getPassword().equals("emp11") || e.getPassword().equals("emp12") || e.getPassword().equals("emp13") || e.getPassword().equals("emp14") || e.getPassword().equals("emp15") || e.getPassword().equals("emp16") || e.getPassword().equals("emp17") || e.getPassword().equals("emp18") || e.getPassword().equals("emp19") || e.getPassword().equals("emp20") || e.getPassword().equals("emp21") || e.getPassword().equals("emp22") || e.getPassword().equals("emp23") || e.getPassword().equals("emp24") || e.getPassword().equals("emp25") || e.getPassword().equals("emp26") || e.getPassword().equals("emp27") || e.getPassword().equals("emp28") || e.getPassword().equals("emp29") || e.getPassword().equals("emp30")) {
            e = hashPassword(e);
        }
        
        for (Role role : e.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        
        return new org.springframework.security.core.userdetails.User(e.getUsername(), e.getPassword(), grantedAuthorities);
    
        
//        Optional<Employee> optionalEmployees = empRepo.findByUsername(username);
//
//        optionalEmployees
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        
//        return optionalEmployees
//                .map(CustomUserDetails::new).get();
       
    }

    private Employee hashPassword(Employee e) {

        String unhashedPass = e.getPassword();
        
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPass = encoder.encode(unhashedPass);
            e.setPassword(hashedPass);
            empRepo.save(e);
        
        
        return e;
        
    }
    
    

}
