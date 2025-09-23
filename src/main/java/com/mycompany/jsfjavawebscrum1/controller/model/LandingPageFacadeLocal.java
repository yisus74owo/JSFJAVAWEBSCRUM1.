/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.jsfjavawebscrum1.controller.model;

import com.mycompany.jsfjavawebscrum1.entities.LandingPage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yisus
 */
@Local
public interface LandingPageFacadeLocal {

    void create(LandingPage landingPage);

    void edit(LandingPage landingPage);

    void remove(LandingPage landingPage);

    LandingPage find(Object id);

    List<LandingPage> findAll();

    List<LandingPage> findRange(int[] range);

    int count();
    
}
