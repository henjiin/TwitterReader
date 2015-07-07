#!/usr/bin/gawk -f
BEGIN {
  RS="\n"
  FS=","
}
{
  username[$2]++;
}
END {
  for (i in username) {
    print username[i],",", i;
  }
}
