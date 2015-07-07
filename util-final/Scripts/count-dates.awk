#!/usr/bin/gawk -f
BEGIN {
  RS="\n"
  FS=","
}
{
  username[$3]++;
}
END {
  for (i in username) {
    print username[i], i;
  }
}
