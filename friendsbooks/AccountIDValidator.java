/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author soundaryalanka
 */
public class AccountIDValidator {
    
    private Pattern pattern;
	  private Matcher matcher;

	  private static final String ACCOUNTID_PATTERN =
              "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d!*#?]{3,10}$";
          

	  public AccountIDValidator(){
		  pattern = Pattern.compile(ACCOUNTID_PATTERN);
                  
	  }
          
	  public boolean validateString(final String useraccountId){

		  matcher = pattern.matcher(useraccountId);
                  
		  return matcher.matches();

	  }
          
          public boolean validateUnique(String useraccountId){
              ArrayList<String> allUserAccountID = UserController.getAllUserAccountID();
              for(String var:allUserAccountID)
              {
                  if(var.equals(useraccountId))
                  {
                      
                      return true;
                  }
              }
              return false;
//              if(allUserAccountID.contains(useraccountId)){
//                  return false;
//              }else{
//                  return true;
//              }
          }
    
}
