/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //    add private lateinit vars for drawerLayout and appBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        Initialize the drawerLayout from the binding variable
        drawerLayout = binding.drawerLayout

//        Link the NavController to the ActionBar with NavigationUI.setupWithNavController
        val navController = this.findNavController(R.id.myNavHostFragment)
//        Link the NavController to our ActionBar.
//        Add the DrawerLayout as the third parameter to setupActionBarWithNavController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        Create an appBarConfiguration with the navController.graph and drawerLayout
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination,
                                                        args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
//        Hook up the navigation UI up to the navigation view
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    //    Override the onSupportNavigateUp method from the activity and call navigateUp in
    //    nav controller.
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
//        Replace navController.navigateUp with NavigationUI.navigateUp with drawerLayout as parameter
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


}
