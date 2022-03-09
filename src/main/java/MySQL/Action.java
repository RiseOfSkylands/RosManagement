package MySQL;

import com.skylands.Globals;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.Properties;

public class Action {

    private FileConfiguration cfg = Globals.cfg;
    public Action() {

    }
    Connection crs = null;
    PreparedStatement psrs = null;
    ResultSet rsrs = null;
    Connection ci = null;
    PreparedStatement psi = null;

    public Connection Connect() {
        System.out.println(cfg.getString("login.ip"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.put("connectTimeout", "5000");
            properties.put("useSSL", "false");
            String dbConnectionString = "jdbc:mysql://"+
                    cfg.getString("login.ip")+":"+
                    cfg.getString("login.port")+"/"+
                    cfg.getString("login.db")+"?user="+
                    cfg.getString("login.user")+"&password="+
                    cfg.getString("login.pass");
            Connection c = DriverManager.getConnection(dbConnectionString, properties);
            return c;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No connection to database!");
        }
        return null;
    }

    ResultSet rs = null;

    public ResultSet Select(String psrss) {
        crs = Connect();
        try {
            psrs = crs.prepareStatement(psrss);
        } catch (SQLException e1) {
            try {
                crs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            e1.printStackTrace();
            return null;
        }
        if (psrs == null) {
            try {
                crs.close();
            } catch (SQLException e) {

                e.printStackTrace();
                return null;
            }
            return null;
        }
        try {
            rsrs = psrs.executeQuery();
        } catch (SQLException e) {
            try {
                crs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                psrs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }

        return rsrs;
    }

    public void InsertUpdate(String psis, String[] vals, String[] objs) {
        ci = Connect();
        try {
            System.out.println(psis);
            psi = ci.prepareStatement(psis);
        } catch (SQLException e1) {
            try {
                ci.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            e1.printStackTrace();
            return;
        }
        if (psi == null) { try {
            ci.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return;
        }
        int i = 1;
        for (String obj : objs) {
            switch(obj.toLowerCase()) {
                case "str":
                    try {
                        psi.setString(i, vals[i-1]);
                    } catch (SQLException e) {
                        try {
                            psi.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            ci.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                        return;
                    }
                    break;
                case "int":
                    try {
                        psi.setInt(i, Integer.parseInt(vals[i-1]));
                    } catch (SQLException e) {
                        try {
                            psi.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            ci.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                        return;
                    }
                    break;
                case "long":
                    try {
                        psi.setLong(i, Long.parseLong(vals[i-1]));
                    } catch (SQLException e) {
                        try {
                            psi.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            ci.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                        return;
                    }
                    break;
                case "bool":
                    try {
                        psi.setBoolean(i, Boolean.parseBoolean(vals[i-1]));
                    } catch (SQLException e) {
                        try {
                            psi.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            ci.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                        return;
                    }
                    break;
                case "double":
                    try {
                        psi.setDouble(i, Double.parseDouble(vals[i-1]));
                    } catch (SQLException e) {
                        try {
                            psi.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            ci.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                        return;
                    }
                    break;
            }
            i++;
        }
        try {
            psi.executeUpdate();
        } catch (SQLException e) {
            try {
                psi.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                ci.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        try {
            psi.close();
        } catch (SQLException e) {
            try {
                psi.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                ci.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }

    }

    public void closeI() {
        try {
            psi.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ci.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeRS() {
        try {
            rsrs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            psrs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            crs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String[] createVals(String... vals) {
        return vals;
    }

    public String[] createObjs(String... objs) {
        return objs;
    }

}

