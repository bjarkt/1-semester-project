/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Acq.IRoom;

import java.util.List;

/**
 *
 * @author Kristian
 */
public interface Spawnable {

    List<IRoom> Spawn(List<IRoom> rooms);
    
}
