namespace :crontask do
  desc "Task to read a unread mail"
  task my_cron: :environment do
  puts "read unread mail"
    MailController.readmail
  end

end
