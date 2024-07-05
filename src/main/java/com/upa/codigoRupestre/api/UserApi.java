package com.upa.codigoRupestre.api;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j		
@RestController  
@RequestMapping("/api/v1/user")
public class UserApi {
	
	@Autowired
	private UserRepository repository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody UserEntity user){
		log.info("Crear User - POST");
		repository.save(user);		
		return ResponseEntity.ok("User created: " + user.getUserName());		
	}	
	
	@RequestMapping(method = RequestMethod.GET, path = "/{userName}")
	public ResponseEntity<Object> getUser(@PathVariable("userName") String userName){
		log.info("fin user " + userName + " GET");
		
		 List<UserEntity> user = repository.findByUserName(userName);
		 
		 if(!user.isEmpty()) {			 
		return ResponseEntity.ok(user.get(0));
		 }else {
			 return ResponseEntity.ok("No existe usuario: " + userName);
		 }			
	}	
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable("id") Integer id,  @RequestBody UserEntity userUpdate){
		log.info("Update User " + userUpdate + " UPDATE");
		
		Optional<UserEntity> updateUser = repository.findById(id);
			
		if(!updateUser.isEmpty()) {
			UserEntity user = updateUser.get(); 
			user.setName(userUpdate.getName());
			user.setLastName(userUpdate.getLastName());
			user.setEmail(userUpdate.getEmail());
			user.setPassword(userUpdate.getPassword());
			
			repository.save(user);
			
			return ResponseEntity.ok("User actualizado de forma exitosa");			
		}else {
			return ResponseEntity.ok("No exxiste el usuario que se quiere actualizar");
		}
		
	}	
	
	@RequestMapping( method=RequestMethod.PATCH, path = "/{id}/{newpassword}")
	public ResponseEntity<Object> updatePasswordUser(@PathVariable("id") Integer id, @PathVariable String newpassword){
		log.info("Update password PATCH");
		
		Optional<UserEntity> updateUser = repository.findById(id);
		
		if(!updateUser.isEmpty()) {
			UserEntity user = updateUser.get();
			user.setPassword(newpassword);
			repository.save(user);
			
			return ResponseEntity.ok("Contraseña actualizado de forma exitosa");
			
		}else {
			return ResponseEntity.ok("No existe el usuario al que se requiera actualizar la contraseña");
		}
		
		
	}	
	
	@RequestMapping(method = RequestMethod.DELETE, path = "{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer id){
		log.info("delete user DELETE");
		
		Optional<UserEntity> deleteUser = repository.findById(id);
		
		if(!deleteUser.isEmpty()) {
			repository.delete(deleteUser.get());			
			return ResponseEntity.ok("Registro eliminado de forma exitosa");
		}else {
			return ResponseEntity.ok("No existe un registro valido para poder eliminarlo");
		}	
		
		
	}	
	
	@RequestMapping(method = RequestMethod.PATCH, path = "/{id}")
	public ResponseEntity<Object> validateUser( @PathVariable("id") Integer id){
		log.info("Validando user HEAD");
		Optional<UserEntity> user = repository.findById(id);
		
		if(!user.isEmpty()) {
			return ResponseEntity.ok("El usiario si existe " + user.get());
		}else {
			return ResponseEntity.noContent().build();
		}
	}	
	
	@RequestMapping(method = RequestMethod.OPTIONS)
	public void validateOption(){
		log.info("method OPTIONS");
		
	}
	

}
