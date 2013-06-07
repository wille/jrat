package com.redpois0n.ip2c;

import java.util.StringTokenizer;

public class LineInfo
  implements Cloneable
{
  private long startIP;
  private long endIP;
  private String id2c;
  private String id3c;
  private String name;
  private int lineNum;

  public LineInfo(long paramLong1, long paramLong2, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.endIP = paramLong2;
    this.id2c = paramString1;
    this.id3c = paramString2;
    this.lineNum = paramInt;
    this.name = paramString3;
    this.startIP = paramLong1;
  }

  public LineInfo(String paramString, int paramInt1, int paramInt2)
  {
    this.lineNum = paramInt2;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "\",");
    int i;
    if (paramInt1 == 1)
    {
      this.startIP = Long.parseLong(localStringTokenizer.nextToken());
      this.endIP = Long.parseLong(localStringTokenizer.nextToken());
      localStringTokenizer.nextToken();
      localStringTokenizer.nextToken();
      this.id2c = localStringTokenizer.nextToken();
      this.id3c = localStringTokenizer.nextToken();
      if (localStringTokenizer.hasMoreElements())
      {
        i = paramString.lastIndexOf('"', paramString.length() - 2);
        this.name = paramString.substring(i + 1, paramString.length() - 1);
      }
    }
    else if (paramInt1 == 0)
    {
      this.startIP = Long.parseLong(localStringTokenizer.nextToken());
      this.endIP = Long.parseLong(localStringTokenizer.nextToken());
      this.id2c = localStringTokenizer.nextToken();
      this.id3c = "";
      this.name = "";
      if (localStringTokenizer.hasMoreElements())
      {
        this.id3c = localStringTokenizer.nextToken();
        if (localStringTokenizer.hasMoreElements())
        {
          i = paramString.lastIndexOf('"', paramString.length() - 2);
          this.name = paramString.substring(i + 1, paramString.length() - 1);
        }
      }
    }
  }

  public long getStartIP()
  {
    return this.startIP;
  }

  public long getEndIP()
  {
    return this.endIP;
  }

  public String getId2c()
  {
    return this.id2c;
  }

  public String getId3c()
  {
    return this.id3c;
  }

  public String getName()
  {
    return this.name;
  }

  public int getLineNum()
  {
    return this.lineNum;
  }

  public void setEndIP(long paramLong)
  {
    this.endIP = paramLong;
  }

  public void setStartIP(long paramLong)
  {
    this.startIP = paramLong;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (paramObject.getClass() != getClass()))
      return false;
    LineInfo localLineInfo = (LineInfo)paramObject;
    return (this.startIP == localLineInfo.getStartIP()) && (this.endIP == localLineInfo.getEndIP()) && (this.id2c != null) && (this.id2c.equals(localLineInfo.getId2c())) && (this.id3c != null) && (this.id3c.equals(getId3c())) && (this.name.equals(localLineInfo.getName()));
  }

  public int hashCode()
  {
    int i = 0;
    if (this.id2c != null)
      i = this.id2c.hashCode();
    if (this.id3c != null)
      i = 42 * i + this.id3c.hashCode();
    if (this.name != null)
      i = 15 * i + this.name.hashCode();
    i = (int)(i + this.startIP);
    i = (int)(i + this.endIP);
    return i;
  }

  public String toString()
  {
    return "Line #" + this.lineNum + ": " + this.startIP + "," + this.endIP + "," + this.id2c + "," + this.id3c + "," + this.name;
  }

  public Object clone()
  {
    return new LineInfo(this.startIP, this.endIP, this.id2c, this.id3c, this.name, this.lineNum);
  }
}