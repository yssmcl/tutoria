package { 'puppet': ensure => 'installed' }
package { 'git': ensure => 'installed' }
package { 'openjdk-8-jdk': ensure => 'installed' }

file { 'agentconfig.sh':
  ensure => 'file',
  path   => '/vagrant/agentconfig.sh',
  owner  => 'ubuntu',
  group  => 'ubuntu',
  mode   => '0755',
  require => Package['puppet'],
}

exec { 'configuracao-puppet-agent':
  command   => "/vagrant/agentconfig.sh",
  logoutput => true,
  require => Package['puppet'],
}

exec { 'git-clone':
  cwd       => '/home/ubuntu',
  command   => '/usr/bin/git clone https://github.com/yssmcl/tutoria.git /home/ubuntu/tutoria',
  logoutput => true,
  user      => 'ubuntu',
  require   => Package['git'],
}

# TODO: avisar quando terminar de compilar ou mostrar o log do gradle
exec { 'appStart':
  cwd       => '/home/ubuntu/tutoria/Code/k-lima-gradle',
  logoutput => true,
  command   => '/bin/echo "Gradle..." && /home/ubuntu/tutoria/Code/k-lima-gradle/gradlew appStart',
  user      => 'ubuntu',
  require   => [Exec['git-clone'], Package['openjdk-8-jdk']],
}
