#!/usr/bin/gawk -f
BEGIN {
  RS="\n"
  FS=","
}
{
  
  {useronDate[$3" " "'"$2"'"]++;}
}
END {
  for (i in useronDate) {
    print useronDate[i], i;
  }
}