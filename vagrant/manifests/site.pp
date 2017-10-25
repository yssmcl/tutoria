# node /\w+\.local$/ {
#   notify { "=== Notificacao teste": }
# }

exec { 'git-pull':
  cwd       => '/home/ubuntu/tutoria/Code/k-lima-gradle',
  command   => '/usr/bin/git pull && /home/ubuntu/tutoria/Code/k-lima-gradle/gradlew appRestart',
  logoutput => true,
  user      => 'ubuntu',
}
