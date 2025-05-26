package com.loficostudios.floralcraftapi.utils;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class FileUtils {
    public static String serializeObjectToString(Object object) {
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream outputStream = new BukkitObjectOutputStream(io);
            outputStream.writeObject(object);
            outputStream.flush();
            byte[] obj = io.toByteArray();

            return Base64.getEncoder().encodeToString(obj);
        } catch (IOException e) {
            Debug.logError(e.getMessage());
            return null;
        }
    }

    public static UUID getUUIDFromHashcode(int hash) {

        byte[] bytes = new byte[4];
        bytes[0] = (byte) (hash >>> 24);
        bytes[1] = (byte) (hash >>> 16);
        bytes[2] = (byte) (hash >>> 8);
        bytes[3] = (byte) hash;

        return UUID.nameUUIDFromBytes(bytes);
    }

    public static Object deserializeStringToObject(String string, Class<?> clazz) {
        byte[] obj = Base64.getDecoder().decode(string);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(obj);

        try {
            BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream);
            Object deserializedObj = bukkitInputStream.readObject();
            if (clazz.isInstance(deserializedObj)) {
                return clazz.cast(deserializedObj);
            } else {
                throw new ClassNotFoundException("Deserialized object is not of the expected type");
            }
        } catch (IOException e) {
            Debug.logError(e.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Debug.logError("Cannot decode base64 byte array to ItemStack");
            return null;
        }
    }

    public static boolean deleteDirectory(File target) {
        if (target.isDirectory()) {
            File[] contents = target.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteDirectory(f);
                }
            }
        }
        return target.delete();
    }

    public static boolean copyFileStructure(File source, File target, List<String> ignore) {
        var pattern = Pattern.compile("[\\\\/:*?\"<>|]");
        if (pattern.matcher(source.getName()).find()) {
            throw new IllegalArgumentException("Source file contains invalid characters: " + source.getName());
        }

        if (pattern.matcher(target.getName()).find()) {
            throw new IllegalArgumentException("Target file contains invalid characters: " + target.getName());
        }
        if (ignore.contains(source.getName())) {
            return true;
        }

        try {
            if (source.isDirectory()) {
                if (!target.exists() && !target.mkdirs()) {
                    throw new IOException("Could not create directories: " + target);
                }
                String[] files = source.list();
                if (files != null) {
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        if (!copyFileStructure(srcFile, destFile, ignore)) {
                            return false;
                        }
                    }
                }
            } else {
                try (InputStream in = new FileInputStream(source);
                     OutputStream out = new FileOutputStream(target)) {
                    byte[] buffer = new byte[8192];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
