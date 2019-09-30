class User < ApplicationRecord

has_many :comments

has_many :articles, dependent: :destroy
  validates:name ,presence: true,length: {minimum:3,maximum:20},
            uniqueness: { case_sensitive: false }



  VALID_EMAIL_REGEX = /\A[\w+\-.]+@[a-z\d\-.]+\.[a-z]+\z/i

  validates :email, presence: true, length: { maximum: 105 },

  uniqueness: { case_sensitive: false },

  format: { with: VALID_EMAIL_REGEX }


  validates:phone_number,presence:true,uniqueness:true

  has_secure_password

  end
