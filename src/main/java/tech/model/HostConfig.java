/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.model;

/**
 *
 * @author myduyqqqqqqq
 */
public class HostConfig {
    private String host;
    private int port;
    private String username;
    private String password;

    public HostConfig() {
    }
    
    
    public HostConfig(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "HostConfig{" + "host=" + host + ", port=" + port + ", username=" + username + ", password=" + password + '}';
    }
    
}
