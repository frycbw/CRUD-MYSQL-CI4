<?php

namespace App\Database\Seeds;

class MahasiswaSeeder extends \CodeIgniter\Database\Seeder
{
    public function run()
    {
        $data1 = [
            'nim'     => '18051204070',
            'nama'   => 'Farry',
            'angkatan' => '2018'
        ];
        $data2 = [
            'nim'     => '18051204062',
            'nama'   => 'Shafir',
            'angkatan' => '2018'
        ];
        $this->db->table('mahasiswa')->insert($data1);
        $this->db->table('mahasiswa')->insert($data2);
    }
}
