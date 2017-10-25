package { 'puppet':       ensure => 'installed' }
package { 'puppetmaster': ensure => 'installed' }

file { ['/etc/puppetlabs', '/etc/puppetlabs/puppet']:
  ensure => 'directory',
  owner  => 'root',
  group  => 'root',
}

file { 'autosign.conf':
  ensure  => 'present',
  content => '*.local',
  path    => '/etc/puppetlabs/puppet/autosign.conf',
  owner   => 'root',
  group   => 'root',
}

file { '/vagrant/masterconfig.sh':
  ensure => 'file',
  path   => '/vagrant/masterconfig.sh',
  owner  => 'vagrant',
  group  => 'vagrant',
  mode   => '0755',
}

exec { 'configuracao-puppet-master':
  command => '/vagrant/masterconfig.sh',
}

file { '/etc/puppet/manifests/site.pp':
  source  => '/vagrant/manifests/site.pp',
  ensure  => 'present',
  owner   => 'root',
  group   => 'root',
  # require => Package['puppet', 'puppetmaster'],
}
