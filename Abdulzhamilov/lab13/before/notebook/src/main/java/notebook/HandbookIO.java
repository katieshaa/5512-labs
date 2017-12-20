package notebook;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandbookIO {
    private ConcurrentHashMap<String,ArrayList<String>> base;
    private BufferedReader  buf;
    private BufferedWriter outbuf;
    private String name;
    public HandbookIO(String Name)
    {
        base = new ConcurrentHashMap<String,ArrayList<String>>();
        try {
            buf = new BufferedReader(new FileReader(Name));
            name = Name;
        }
        catch (Exception e)
        {
        }
    }
    public  void read()
    {
        try {
            String[] str;
            String tmp;
            ArrayList<String> list;
            while ((tmp = buf.readLine()) != null) {
                tmp.trim();
                str = tmp.split(" ");
                list = new ArrayList<>();
                for (int i = 1; i < str.length; i++) {
                    if (check(str[i]))
                        list.add(str[i]);
                }
                if (!base.containsKey(str[0])) {
                    base.put(str[0], list);
                } else {
                    boolean flag;
                    ArrayList<String> listTmp = base.get(str[0]);
                    for (int j = 0; j < list.size(); j++) {
                        flag = true;
                        for (int z = 0; z < listTmp.size(); z++) {
                            if (list.get(j).equals(listTmp.get(z)))
                                flag = false;
                        }
                        if (flag)
                            listTmp.add(list.get(j));
                    }
                    base.remove(str[0]);
                    base.put(str[0], listTmp);

                }
            }
        }
        catch (Exception e)
        {
        	System.out.println("suka");
        }
        try {
            buf.close();
        }
        catch (Exception e){}
    }
    private boolean check(String str)
    {
        for (int i = 0; i < str.length(); i++) {
            if ('0' <= str.charAt(i) && str.charAt(i) >= '9')
                continue;
            //else
                //return false;
        }
        return  true;
    }
    public synchronized void addUser(String name, String phones)
    {
        if(base.containsKey(name))
        {
            return;
        }
        phones.trim();
        String[] str = phones.split(" ");
        put(name,str);
        write();

    }
    public ConcurrentHashMap<String,ArrayList<String>> getBase()
    {
        return  base;
    }


    public synchronized void addPhone(String name, String phone)
    {
        if (base.containsKey(name))
        {
            phone.trim();
            String[] str = phone.split(" ");
           put(name,str);
            write();
        }
    }

    private void write()
    {
        try {
            outbuf = new BufferedWriter(new FileWriter(name));
            StringBuilder str = new StringBuilder();
            ArrayList<String> phones;
            for(Map.Entry<String,ArrayList<String>>entry: base.entrySet())
            {
                str.append(entry.getKey()+  " ");
                phones = base.get(entry.getKey());
                for (int i = 0; i < phones.size(); i++)
                {
                    str.append( phones.get(i) + " ");
                }
                str.append("\n");
            }
            outbuf.write(str.toString());
            outbuf.flush();
            outbuf.close();
        }
        catch (Exception e){}
    }
    private void put(String name, String[] phones)
    {
        ArrayList<String >list = new ArrayList<>();
        for (int i = 0; i < phones.length; i++) {
            if (check(phones[i]))
                list.add(phones[i]);
        }
        if (!base.containsKey(name)) {
            base.put(name, list);
        } else {
            boolean flag;
            ArrayList<String> listTmp = base.get(name);
            for (int j = 0; j < list.size(); j++) {
                flag = true;
                for (int z = 0; z < listTmp.size(); z++) {
                    if (list.get(j).equals(listTmp.get(z)))
                        flag = false;
                }
                if (flag)
                    listTmp.add(list.get(j));
            }
            base.remove(name);
            base.put(name, listTmp);

        }
    }
    }

