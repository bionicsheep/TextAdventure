/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventure;

public interface Responder {
    
    public String getResponse(String verb, String noun, GameWorld world);
    
}
