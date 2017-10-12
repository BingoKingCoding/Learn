package com.king.learn.app.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * <SD卡工具类>
 */

public class DDFileUtil
{
    private static final String HTTP_CACHE_DIR = "http";
    public static final String IMAGE_CACHE_DIR = "image";
    private static final String AD_IMAGE_CACHE_DIR = "adimages";
    private static Context context = Utils.getApp();

    /**
     * sd卡是否存在
     *
     * @return
     */
    public static boolean isSdcardExist()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得sd卡根目录file对象引用
     *
     * @return
     */
    public static File getSdCardFile()
    {
        if (isSdcardExist())
        {
            return Environment.getExternalStorageDirectory();
        } else
        {
            return null;
        }
    }

    /**
     * 判断路径下的文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path)
    {
        boolean result = false;
        if (path != null)
        {
            result = new File(path).exists();
        }
        return result;
    }

    /**
     * @return
     */
    public static boolean isADimageExist(String paramString)
    {
        return isFileExist(getADImageCacheDir().getAbsolutePath() + "/" + paramString);
    }


    /**
     * 获得公共的相册目录
     *
     * @return
     */
    public static File getPicturesDir()
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        return dir;
    }

    public static boolean copy(String fromPath, String toPath)
    {
        boolean result = false;
        File from = new File(fromPath);
        if (!from.exists())
        {
            return result;
        }

        File toFile = new File(toPath);
        deleteFileOrDir(toFile);
        File toDir = toFile.getParentFile();
        if (toDir.exists() || toDir.mkdirs())
        {
            FileInputStream in = null;
            FileOutputStream out = null;
            try
            {
                in = new FileInputStream(from);
                out = new FileOutputStream(toFile);
                IOUtil.copy(in, out);
                result = true;
            } catch (Throwable ex)
            {
                result = false;
            } finally
            {
                IOUtil.closeQuietly(in);
                IOUtil.closeQuietly(out);
            }
        }
        return result;
    }

    public static boolean writeToFile(String content, String filePath)
    {
        PrintWriter pw = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                file.createNewFile();
            }
            pw = new PrintWriter(file);
            pw.write(content);
            pw.flush();
            return true;
        } catch (Exception e)
        {
            return false;
        } finally
        {
            IOUtil.closeQuietly(pw);
        }
    }

    public static String readFromFile(String filePath)
    {
        BufferedReader reader = null;
        try
        {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            StringBuffer strBuffer = new StringBuffer();
            String strLine = "";
            while ((strLine = reader.readLine()) != null)
            {
                strBuffer.append(strLine);
            }
            return strBuffer.toString();
        } catch (Exception e)
        {
            return null;
        } finally
        {
            IOUtil.closeQuietly(reader);
        }
    }

    /**
     * 获得文件或者文件夹下所有文件的大小
     *
     * @param file
     * @return
     */
    public static long getFileOrDirSize(File file)
    {
        if (file == null)
        {
            return 0;
        }
        if (!file.exists())
        {
            return 0;
        }
        if (!file.isDirectory())
        {
            return file.length();
        }
        long length = 0;
        File[] list = file.listFiles();
        if (list != null)
        {
            for (File item : list)
            {
                length += getFileOrDirSize(item);
            }
        }
        return length;
    }

    public static boolean deleteFileOrDir(File path)
    {
        if (path == null || !path.exists())
        {
            return true;
        }
        if (path.isFile())
        {
            return path.delete();
        }
        File[] files = path.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

    /**
     * 格式化文件大小
     *
     * @param fileLength
     * @return
     */
    public static String formatFileSize(long fileLength)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLength <= 0)
        {
            fileSizeString = "0.00B";
        } else if (fileLength < 1024)
        {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576)
        {
            fileSizeString = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824)
        {
            fileSizeString = df.format((double) fileLength / 1048576) + "MB";
        } else
        {
            fileSizeString = df.format((double) fileLength / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static String getExtString(File file)
    {
        String extString = null;
        if (file != null && file.exists())
        {
            String path = file.getAbsolutePath();
            int dotIndex = path.lastIndexOf(".");
            if (dotIndex >= 0)
            {
                extString = path.substring(dotIndex + 1);
            }
        }
        return extString;
    }

    public static String getMimeType(File file)
    {
        String mime = null;
        String extString = getExtString(file);
        mime = DDMimeTypeUtil.getMimeType(extString);
        return mime;
    }

    public static File createDefaultFileUnderDir(File dir)
    {
        return createDefaultFileUnderDir(dir, null);
    }

    public static File createDefaultFileUnderDir(File dir, String ext)
    {
        try
        {
            if (ext == null)
            {
                ext = "";
            }

            long current = System.currentTimeMillis();
            File file = new File(dir, String.valueOf(current + ext));
            while (file.exists())
            {
                current++;
                file = new File(dir, String.valueOf(current + ext));
            }
            return file;
        } catch (Exception e)
        {
            return null;
        }
    }

    public static void copyAnrToCache()
    {
        try
        {
            File saveDir = getCacheDirectory("anr");
            if (saveDir == null)
            {
                return;
            }
            File anrFile = new File("/data/anr/traces.txt");
            if (!anrFile.exists())
            {
                return;
            }
            String content = readFromFile(anrFile.getAbsolutePath());
            if (TextUtils.isEmpty(content))
            {
                return;
            }

            File saveFile = new File(saveDir, "arn.txt");
            if (!saveFile.exists())
            {
                saveFile.createNewFile();
            }
            writeToFile(content, saveFile.getAbsolutePath());
            anrFile.delete();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 获取SD卡缓存目录
     *
     * @param type 文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *             否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *             {@link android.os.Environment#DIRECTORY_MUSIC},
     *             {@link android.os.Environment#DIRECTORY_PODCASTS},
     *             {@link android.os.Environment#DIRECTORY_RINGTONES},
     *             {@link android.os.Environment#DIRECTORY_ALARMS},
     *             {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
     *             {@link android.os.Environment#DIRECTORY_PICTURES}, or
     *             {@link android.os.Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(String type)
    {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            if (TextUtils.isEmpty(type))
            {
                appCacheDir = context.getExternalCacheDir();
            } else
            {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null)
            {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + type);
            }

            if (appCacheDir == null)
            {
                Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else
            {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs())
                {
                    Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else
        {
            Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(String type)
    {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type))
        {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else
        {
            appCacheDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists() && !appCacheDir.mkdirs())
        {
            Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param type 文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(String type)
    {
        File appCacheDir = getExternalCacheDirectory(type);
        if (appCacheDir == null)
        {
            appCacheDir = getInternalCacheDirectory(type);
        }

        if (appCacheDir == null)
        {
            Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else
        {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs())
            {
                Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }

    public static File getImageCacheDir()
    {
        return getCacheDirectory(IMAGE_CACHE_DIR);
    }

    public static File creatImageCache(String fileName)
    {
        File dir = new File(getImageCacheDir().getAbsolutePath());
        if (!dir.exists())
        {
            boolean create = dir.mkdirs();
            Timber.d("create = " + create);
        }
        return new File(getImageCacheDir(), fileName);
    }

    public static File getADImageCacheDir()
    {
        return getCacheDirectory(AD_IMAGE_CACHE_DIR);
    }


    public static File createADImageFile(String fileName) throws IOException
    {
        File dir = new File(getADImageCacheDir().getAbsolutePath());
        if (!dir.exists())
        {
            boolean create = dir.mkdirs();
            Timber.d("create = " + create);
        }
        return new File(getADImageCacheDir().getAbsolutePath(), fileName);
    }

    /**
     * @param
     * @Description 得到 adimages 文件夹下的所有文件
     */
    public static List<String> getAllAD()
    {
        File file = new File(getADImageCacheDir().getAbsolutePath());
        File[] fileList = file.listFiles();
        List<String> list = new ArrayList<>();
        if (null != fileList)
        {
            for (File f : fileList)
            {
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }

}
